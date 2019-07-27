import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {MaterialModule} from './material.module';
import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {FormsModule} from '@angular/forms';
import {HomeComponent} from './components/home/home.component';
import {HttpClientModule} from '@angular/common/http';
import { CanvasComponent } from './components/canvas/canvas.component';
import { SelfComponent } from './components/self/self.component';
import { AdminTableComponent } from './components/admin-table/admin-table.component';
import { AdminControlsComponent } from './components/admin-controls/admin-controls.component';
import {MatCardModule, MatIconModule, MatProgressSpinnerModule, MatSidenavModule, MatListModule, MatToolbarModule} from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    CanvasComponent,
    SelfComponent,
    AdminTableComponent,
    AdminTableComponent,
    AdminControlsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    MaterialModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatListModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
