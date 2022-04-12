import { Group } from './group.model';
import { Presence } from './presence.model';
import { Student } from './student.model';

export class StudentGroup {
  id?: number;

  mark: number;

  groupId?: number;
  studentId?: number;
  presenceIdList?: number;

  constructor() {
    this.mark = 0;
  }
}
