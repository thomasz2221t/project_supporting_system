export class Semester {
  id?: number;
  fieldOfStudy: string;
  year: number;
  semester: number;

  groupIdList?: number[];
  studentSemesterIdList?: number[];

  constructor() {
    this.fieldOfStudy = '';
    this.year = 0;
    this.semester = 0;
  }
}
