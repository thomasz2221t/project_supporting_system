import { StudentGroup } from './student-group';
import { StudentSemester } from './student-semester';

export interface Student {
  albumNo?: string;
  firstName: string;
  lastName: string;

  birthDate: Date;

  studentGroupIdList?: number[];
  studentSemesterIdList?: number[];
}