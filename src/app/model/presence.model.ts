import { Meeting } from './meeting.model';
import { StudentGroup } from './student-group.model';

export class Presence {
  id?: number;

  wasPresent: boolean;
  meetingDate: Date;

  meetingId?: number;

  studentGroupId?: number;

  constructor() {
    this.wasPresent = false;
    this.meetingDate = new Date();
  }
}
