import { createAction, props } from '@ngrx/store';
import { User } from './model/user.model';

export const login = createAction('[Menu] User Login', props<{ user: User }>());

export const logout = createAction('[User dropdown] Logout');
