import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { initializer } from 'src/utils/app-inits';
import {LoginComponent} from "./login/login.component";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule} from "@angular/forms";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatCardModule} from "@angular/material/card";
import {NgxTweetModule} from "ngx-tweet";
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { MatDialogModule } from "@angular/material/dialog";
import {
  NbAccordionModule,
  NbButtonModule, NbCardModule,
  NbChatModule, NbCheckboxModule, NbIconModule,
  NbLayoutModule, NbListModule,
  NbMenuModule,
  NbSidebarModule, NbTableModule,
  NbThemeModule,
  NbToastrModule, NbToggleModule
} from "@nebular/theme";
import { ChatGPTComponent } from "./chat-gpt/chat-gpt.component";
import { MatButtonToggleModule } from "@angular/material/button-toggle";
import { NbEvaIconsModule } from "@nebular/eva-icons";
import { NewHomeComponent } from './new-home/new-home.component';
import {AuthInterceptor} from "./auth/auth.interceptor";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ChatGPTComponent,
    NewHomeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule, BrowserAnimationsModule, FormsModule, MatToolbarModule, MatFormFieldModule, MatCardModule,
    NgxTweetModule, MatDialogModule,
    NbChatModule.forRoot({ messageGoogleMapKey: "AIzaSyA21jY_Duhpd9We2h-ngMHri79ridaXwt8" }),
    NbToastrModule.forRoot(), NbThemeModule.forRoot(), NbLayoutModule, NbSidebarModule.forRoot(), NbMenuModule.forRoot(), MatButtonToggleModule, NbButtonModule,
    NbEvaIconsModule, NbTableModule, NbCardModule, NbListModule, NbCheckboxModule, NbToggleModule, NbAccordionModule, NbIconModule
  ],
  providers: [
    // add this provider
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
// @ts-ignore
export class AppModule {
}
