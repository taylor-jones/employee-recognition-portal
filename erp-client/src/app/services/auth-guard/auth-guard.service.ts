import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthenticationService} from '../authentication/authentication.service';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class AuthGuardService implements CanActivate {
  constructor(private router: Router, private authService: AuthenticationService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.authService.isAuthenticated()
      .pipe(
        map(
          isAuthenticated => {
            if (!isAuthenticated) {
              this.router.navigate(['login']);
              return false;
            }
            return isAuthenticated;
          }
        )
      );
  }
}
