import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthGuard } from "./auth/auth.guard";
import { ChatGPTComponent } from "./chat-gpt/chat-gpt.component";
import { NewHomeComponent } from "./new-home/new-home.component";
import {LoginComponent} from "./login/login.component";

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'home',
    component: NewHomeComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'assistants/:type',
    component: ChatGPTComponent,
    canActivate: [AuthGuard],
    // data: { roles: ['openapi'] }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}


