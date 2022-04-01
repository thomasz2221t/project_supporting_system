import { Meeting } from './meeting';
import { StudentGroup } from './student-group';

export interface Presence {
  id?: number;

  wasPresent: boolean;
  meetingDate: Date;

  meetingId?: number;

  studentGroupId?: number;
}
