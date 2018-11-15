import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { MatProgressSpinnerModule } from '@angular/material';

import { OverlayRequestResponseInterceptor } from './overlay-request-response.interceptor';
import { OverlayComponent } from './overlay.component';


@NgModule({
  imports: [CommonModule,MatProgressSpinnerModule],
  exports: [OverlayComponent],
  declarations: [OverlayComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: OverlayRequestResponseInterceptor,
      multi: true,
    }
  ]
})
export class OverlayModule  {    // Ensure that OverlayModule is only loaded into AppModule

  // Looks for the module in the parent injector to see if it's already been loaded (only want it loaded once)
  constructor() {
  
  }
}
