import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { login } from './store/app.actions';
import { AuthState } from './store/reducers';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'project-supporting-system-front-end';

  constructor(private store: Store<AuthState>) {}

  ngOnInit() {
    const userProfile = localStorage.getItem('user');

    if (userProfile) {
      this.store.dispatch(login({ user: JSON.parse(userProfile) }));
    }
  }
}
