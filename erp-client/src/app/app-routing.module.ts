import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { CanvasComponent } from './components/canvas/canvas.component';
import { AwardComponent } from './components/award/award.component';
import { AwardFormComponent } from './components/award/award-form/award-form.component';
import { AuthGuardService } from './services/auth-guard/auth-guard.service';
import { AdminHomeComponent } from './components/admin/admin-home/admin-home.component';
import { SelfComponent } from './components/self/self.component';

const routes: Routes = [
	{ path: '', component: HomeComponent, canActivate: [AuthGuardService] },
	{ path: 'awards', component: AwardComponent, canActivate: [AuthGuardService] },
  { path: 'canvas', component: CanvasComponent, canActivate: [AuthGuardService] },
	{ path: 'create-award', component: AwardFormComponent, canActivate: [AuthGuardService] },
  { path: 'login', component: LoginComponent },
  { path: 'me', component: SelfComponent, canActivate: [AuthGuardService] },
  { path: 'admin', component: AdminHomeComponent, canActivate: [AuthGuardService] },
  { path: '**', redirectTo: 'login' },
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
