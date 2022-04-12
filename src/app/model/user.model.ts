export class User {
  id: string;
  username: string;
  roles: string[];

  constructor() {
    this.id = '';
    this.username = '';
    this.roles = [];
  }
}
