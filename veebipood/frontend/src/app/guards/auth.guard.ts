import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router); // ümbersuunamiseks kui on false
  let response: boolean | Promise<boolean> = false;
  authService.loggedInStatus.subscribe(res => {
    if(res.admin) {
      response = true;
    } else {
      response = router.navigateByUrl("/login");
    }
  });
  return response;
};
