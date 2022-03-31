import { Topic } from './topic';
import { Semester } from './semester';
import { Meeting } from './meeting';
import { StudentGroup } from './student-group';

export interface Group {
  id: number;
  groupState: GroupState;

  semester?: Semester;
  topic?: Topic;
  meeting?: Meeting;
  studentGroupList?: StudentGroup[];
}
