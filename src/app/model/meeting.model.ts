import { Group } from './group.model';
import { Presence } from './presence.model';

export class Meeting {
  id?: number;
  date: Date;
  groupId?: number;
  presenceIdList?: number[];
  constructor() {
    this.date = new Date();
  }
}
