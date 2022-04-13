export interface Semester {
  id?: number;
  fieldOfStudy: string;
  year: number;
  semester: number;

  groupIdList?: number[];
  studentSemesterIdList?: number[];
}
