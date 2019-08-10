// Angular Components
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

// Root Directory Components
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {MaterialModule} from './material.module';
import {CookieService} from 'ngx-cookie-service';

// Page Components
import {AdminControlsComponent} from './components/admin/admin-controls/admin-controls.component';
import {AdminHomeComponent} from './components/admin/admin-home/admin-home.component';
import {AdminTableComponent} from './components/admin/admin-table/admin-table.component';
import {AwardComponent} from './components/award/award.component';
import {CanvasComponent} from './components/canvas/canvas.component';
// import { CreateAwardComponent } from './components/award/createAward.component';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {SelfComponent} from './components/self/self.component';
import {MyPageComponent} from './components/my-page/my-page.component';
import {AccountRecoveryComponent} from './components/account-recovery/account-recovery/account-recovery.component';
import {ChangePasswordComponent} from './components/account-recovery/change-password/change-password.component';
import { CreateAccountComponent } from './components/createAccount/create-account/create-account.component';
import { UsernameSelectionComponent } from './components/createAccount/username-selection/username-selection.component';
import { SetRecoveryQuestionsComponent } from './components/createAccount/set-recovery-questions/set-recovery-questions.component';

// Pipes
import {ConvertTimePipe} from './pipes/convert-time.pipe';
import { SignatureInputComponent } from './components/createAccount/signature-input/signature-input.component';

@NgModule({
  declarations: [
    AdminHomeComponent,
    AdminTableComponent,
    AdminControlsComponent,
    AppComponent,
    AwardComponent,
    CanvasComponent,
    HomeComponent,
    LoginComponent,
    MyPageComponent,
    SelfComponent,
    ConvertTimePipe,
    AccountRecoveryComponent,
    ChangePasswordComponent,
    CreateAccountComponent,
    SetRecoveryQuestionsComponent,
    UsernameSelectionComponent,
    SignatureInputComponent,
  ],
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  exports: [],
  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
