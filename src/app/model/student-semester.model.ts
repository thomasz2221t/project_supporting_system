import { Semester } from './semester.model';
import { Student } from './student.model';

export class StudentSemester {
  id?: number;
  semesterNo: number;
  semesterId?: number;
  studentId?: number;
  constructor() {
    this.semesterNo = 0;
  }
}
