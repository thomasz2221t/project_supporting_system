import { Semester } from './semester';
import { Student } from './student';

export interface StudentSemester {
  id?: number;
  semesterNo: number;
  semester?: Semester;
  student?: Student;
}
