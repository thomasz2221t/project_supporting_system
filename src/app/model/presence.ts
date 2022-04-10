import { Meeting } from './meeting';
import { StudentGroup } from './student-group';

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
