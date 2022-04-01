import { Topic } from './topic';

export interface Lecturer {
  id?: number;
  firstName: string;
  lastName: string;
  subject: string;
  academicDegree: string;
  catherdral: string;

  topics?: Topic[];
}
