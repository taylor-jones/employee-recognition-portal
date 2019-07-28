// Angular Components
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

// Root Directory Components
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material.module';

// Page Components
import { AdminControlsComponent } from './components/admin-controls/admin-controls.component';
import { AdminTableComponent } from './components/admin-table/admin-table.component';
import { AwardComponent } from './components/award/award.component';
import { CanvasComponent } from './components/canvas/canvas.component';
import { CreateAwardComponent } from './components/award/createAward.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { SelfComponent } from './components/self/self.component';


@NgModule({
	declarations: [
    AdminTableComponent,
    AdminTableComponent,
    AdminControlsComponent,
		AppComponent,
    AwardComponent,
    CanvasComponent,
		CreateAwardComponent,
    HomeComponent,
		LoginComponent,
    SelfComponent,
	],
	imports: [
    AppRoutingModule,
		BrowserAnimationsModule,
		BrowserModule,
		FormsModule,
		HttpClientModule,
    MaterialModule,
    ReactiveFormsModule,
    NoopAnimationsModule
  ],
  exports: [],
	providers: [],
	bootstrap: [ AppComponent ]
})
export class AppModule {}
