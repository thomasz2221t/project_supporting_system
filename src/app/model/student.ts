import { StudentGroup } from './student-group';
import { StudentSemester } from './student-semester';

export class Student {
  albumNo?: string;
  firstName: string;
  lastName: string;

  birthDate: Date;

  studentGroupIdList?: number[];
  studentSemesterIdList?: number[];
  constructor() {
    this.firstName = '';
    this.lastName = '';
    this.birthDate = new Date();
  }
}
