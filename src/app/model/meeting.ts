import { Group } from './group';
import { Presence } from './presence';

export class Meeting {
  id?: number;
  date: Date;
  groupId?: number;
  presenceIdList?: number[];
  constructor(){
    this.date = new Date();
  }
}
