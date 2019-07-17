import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../authentication/authentication.service';

@Injectable({
	providedIn: 'root'
})

export class AuthGuardService implements CanActivate {
	constructor(private router: Router, private authService: AuthenticationService) {}

	canActivate(
		route: ActivatedRouteSnapshot,
		state: RouterStateSnapshot
	): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
		if (this.authService.user) {
			return true;
		} else {
			this.router.navigate([ '/login' ]);
			return false;
		}
	}
}
