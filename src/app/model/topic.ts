import { Group } from './group';
import { Lecturer } from './lecturer';

export interface Topic {
  id?: number;
  topicName: string;
  description: string;

  lecturer?: Lecturer;
  groups?: Group[];
}
