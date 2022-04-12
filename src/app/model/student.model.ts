import { StudentGroup } from './student-group.model';
import { StudentSemester } from './student-semester.model';

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
