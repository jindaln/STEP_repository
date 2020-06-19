// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import com.google.sps.TimeRange;
import java.util.Comparator;
import com.google.sps.Event;

//The purpose of FindMeetingQuery is to find a time range such that all attendees required at
//a particular meeting are available, which requires checking all known events and 
//making sure the meeting request and other events don't overlap for any of the attendees. 
public final class FindMeetingQuery {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        System.out.println("THen end of the day is: " + TimeRange.END_OF_DAY);
        long duration = request.getDuration();
        Collection<String> optionalAttendees = request.getOptionalAttendees();
        Collection<String> mandatoryAttendees = request.getAttendees();
        Collection<String> allAttendees = new ArrayList<String>();
        Collection<TimeRange> meetingTimes = new ArrayList<TimeRange>();
        for (String attendee : mandatoryAttendees){
            allAttendees.add(attendee);
        }
        for (String attendee : optionalAttendees){
            allAttendees.add(attendee);
        }
        if (mandatoryAttendees.isEmpty()){
            meetingTimes = returnMeetingTimes(events, optionalAttendees, duration);
        }
        else{
            meetingTimes = returnMeetingTimes(events, allAttendees, duration).isEmpty() ? 
            returnMeetingTimes(events, mandatoryAttendees, duration) : returnMeetingTimes(events, allAttendees, duration);
        }
        return meetingTimes;
    }

    private boolean attendeesAtEvent(Event event, Collection<String> attendees){
        for (String attendee : attendees){
            if (event.getAttendees().contains(attendee)){
                return true;
            }
        }
        return false;
    }

    private void addMeetingTime(int startNext, int endPrevious, long duration, Collection<TimeRange> meetingTimes){
        if(startNext - endPrevious >= duration){
            meetingTimes.add(TimeRange.fromStartEnd(endPrevious, startNext, false));
        }
    }

    private Collection<TimeRange> returnMeetingTimes(Collection<Event> events, Collection<String> attendees, long duration) {
        Event prevAttendedEvent = new Event("Start", TimeRange.fromStartEnd(0, 0, false), Collections.emptySet());
        List<Event> eventList = new ArrayList<Event> (events);
        Collections.sort(eventList, ORDER_BY_START);
        Collection<TimeRange> meetingTimes = new ArrayList<TimeRange>();
        for (Event event : eventList){
            if (attendeesAtEvent(event, attendees)){
                if (event.getWhen().overlaps(prevAttendedEvent.getWhen())){
                    prevAttendedEvent = prevAttendedEvent.getWhen().end() < event.getWhen().end() ? event : prevAttendedEvent;
                }
                else{
                    addMeetingTime(event.getWhen().start(), prevAttendedEvent.getWhen().end(), duration, meetingTimes);
                    prevAttendedEvent = event;
                }
            }
        }
        addMeetingTime(TimeRange.END_OF_DAY + 1, prevAttendedEvent.getWhen().end(), duration, meetingTimes);
        return meetingTimes;
    }

    private static final Comparator<Event> ORDER_BY_START = new Comparator<Event>() {
        @Override
        public int compare(Event a, Event b) {
            return Long.compare(a.getWhen().start(), b.getWhen().start());
        }
    };
}
