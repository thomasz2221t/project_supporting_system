import { Group } from './group';
import { StudentSemester } from './student-semester';

export interface Semester {
  id: number;
  fieldOfStudy: string;
  year: number;
  semester: number;

  groups?: Group[];
  studentSemesterList?: StudentSemester[];
}
