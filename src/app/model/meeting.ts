import { Group } from './group';
import { Presence } from './presence';

export interface Meeting {
  id: number;
  date: Date;
  groupList?: Group[];
  presenceList?: Presence[];
}
