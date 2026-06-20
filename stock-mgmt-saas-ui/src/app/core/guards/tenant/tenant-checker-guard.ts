import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { TokenService } from '../../token/token-service';

export const tenantCheckerGuard: CanActivateFn = async (route, state) => {
  const tokenService = inject(TokenService);
  if (!tokenService.isTenantUser) {
    await tokenService.logout();
    return false;
  }
  return true;
};
