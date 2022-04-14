import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { OAuthModule } from 'angular-oauth2-oidc';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { MenuComponent } from './components/menu/menu.component';
import { OkDialogComponent } from './shared/dialog/ok-dialog/ok-dialog.component';
import { ErrorDialogComponent } from './shared/dialog/error-dialog/error-dialog.component';
import { CollapseElement } from './components/menu/menu.component';
import { HomePageComponent } from './pages/home/home-page/home-page.component';
import { ProgressSpinnerComponent } from './shared/dialog/progress-spinner/progress-spinner.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialogModule } from '@angular/material/dialog';
import { HttpClientModule } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { environment } from '../environments/environment';
import * as fromAuth from './store/reducers';
import { EffectsModule } from '@ngrx/effects';
import { AppEffects } from './store/app.effects';
import { RouterState, StoreRouterConnectingModule } from '@ngrx/router-store';
import { reducers } from './store/app-store';
import { metaReducers } from './store/app-store/index';
import { DialogService } from './shared/services/dialog.service';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    OkDialogComponent,
    ErrorDialogComponent,
    CollapseElement,
    HomePageComponent,
    ProgressSpinnerComponent,
  ],
  imports: [
    BrowserModule,
    OAuthModule.forRoot(),
    AppRoutingModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    HttpClientModule,
    StoreModule.forRoot(reducers, {
      metaReducers,
      runtimeChecks: {
        strictStateImmutability: true,
        strictActionImmutability: true,
        strictActionSerializability: true,
        strictStateSerializability: true,
      },
    }),
    !environment.production
      ? StoreDevtoolsModule.instrument()
      : [EffectsModule],
    StoreModule.forFeature(fromAuth.authFeatureKey, fromAuth.authReducer),
    EffectsModule.forRoot([AppEffects]),
    StoreRouterConnectingModule.forRoot({
      stateKey: 'router',
      routerState: RouterState.Minimal,
    }),
  ],
  exports: [CollapseElement],
  providers: [DialogService],
  bootstrap: [AppComponent],
  entryComponents: [OkDialogComponent],
})
export class AppModule {}
