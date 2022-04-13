export interface Topic {
  id?: number;
  topicName: string;
  description: string;

  lecturerId?: number;
  groupIdList?: number[];
}
