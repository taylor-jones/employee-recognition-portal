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
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {SelfComponent} from './components/self/self.component';
import {MyPageComponent} from './components/my-page/my-page.component';
import {AccountRecoveryComponent} from './components/account-recovery/account-recovery/account-recovery.component';
import {ChangePasswordComponent} from './components/account-recovery/change-password/change-password.component';
import {ReportComponent} from './components/report/report.component';

// Pipes
import {ConvertTimePipe} from './pipes/convert-time.pipe';

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
    ReportComponent,
    ConvertTimePipe,
    AccountRecoveryComponent,
    ChangePasswordComponent,
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
