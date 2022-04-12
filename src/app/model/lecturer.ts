import { Topic } from '../topics/model/topic';

export class Lecturer {
  id?: number;
  firstName: string;
  lastName: string;
  subject: string;
  academicDegree: string;
  catherdral: string;

  topicIdList?: number[];

  constructor() {
    this.firstName = '';
    this.lastName = '';
    this.subject = '';
    this.academicDegree = '';
    this.catherdral = '';
  }
}
