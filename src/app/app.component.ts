import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { login } from './store/app.actions';
import { AuthState } from './store/reducers';
import { TopicsState } from './topics/store/reducers';
import { loadTopicsFromObjectArray } from './topics/store/topics.actions';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'project-supporting-system-front-end';

  constructor(
    private authStore: Store<AuthState>,
    private topicStore: Store<TopicsState>
  ) {}

  ngOnInit() {
    const userProfile = localStorage.getItem('user');

    if (userProfile) {
      this.authStore.dispatch(login({ user: JSON.parse(userProfile) }));
    }
  }
}
