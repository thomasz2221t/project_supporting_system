
export class Topic {
  id?: number;
  topicName: string;
  description: string;

  lecturerId?: number;
  groupIdList?: number[];
  constructor(){
    this.topicName = '';
    this.description = '';
  }
}
