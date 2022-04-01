import { Meeting } from './meeting';
import { StudentGroup } from './student-group';

export interface Presence {
  id?: number;

  wasPresent: boolean;
  meetingDate: Date;

  meeting?: Meeting;

  studentGroup?: StudentGroup;
}
