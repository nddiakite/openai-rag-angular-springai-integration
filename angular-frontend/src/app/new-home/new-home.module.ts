import { RouterModule } from "@angular/router"; // we also need angular router for Nebular to function properly
import {
  NbSidebarModule,
  NbLayoutModule,
  NbButtonModule,
  NbLayoutComponent,
  NbLayoutColumnComponent,
  NbSidebarComponent, NbMenuModule, NbThemeModule, NbIconModule, NbAccordionModule, NbAccordionComponent
} from "@nebular/theme";
import { NgModule } from "@angular/core";

@NgModule({
  imports: [
    RouterModule,
    NbLayoutModule,
    NbSidebarModule,
    NbButtonModule,
    NbMenuModule,
    NbThemeModule,
    NbIconModule,
    NbAccordionModule
  ],
  declarations: [
    NbLayoutComponent,
    NbLayoutColumnComponent,
    NbSidebarComponent,
    NbAccordionComponent
  ],
})

export class NewHomeModule {

}
