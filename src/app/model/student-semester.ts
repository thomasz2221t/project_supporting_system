import { Semester } from './semester';
import { Student } from './student';

export class StudentSemester {
  id?: number;
  semesterNo: number;
  semesterId?: number;
  studentId?: number;
  constructor() {
    this.semesterNo = 0;
  }
}
