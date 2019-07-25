import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { CanvasComponent } from './components/canvas/canvas.component';
import { AwardComponent } from './components/award/award.component';
import { CreateAwardComponent } from './components/award/createAward.component';
import { AuthGuardService } from './services/auth-guard/auth-guard.service';

const routes: Routes = [
	{ path: 'login', component: LoginComponent },
	{ path: '', component: HomeComponent, canActivate: [ AuthGuardService ] },
	{ path: 'canvas', component: CanvasComponent },
	{ path: 'awards', component: AwardComponent },
	{ path: 'createAward', component: CreateAwardComponent },
	{ path: '**', redirectTo: 'login' }
];

@NgModule({
	imports: [ RouterModule.forRoot(routes) ],
	exports: [ RouterModule ]
})
export class AppRoutingModule {}
