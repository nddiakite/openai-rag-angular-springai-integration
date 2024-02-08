import { ChangeDetectionStrategy, Component } from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { NbMenuItem, NbOverlayService, NbSidebarService, NbToastrService } from "@nebular/theme";
import { environment } from "../../environment/environment";
import {AuthService} from "../auth/auth.service";

@Component({
  selector: 'app-new-home',
  templateUrl: './new-home.component.html',
  styleUrls: ['./new-home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NewHomeComponent {
  accessToken: string;
  client: HttpClient;
  userId: any;
  isUserLoggedIn: boolean;
  sidebarItems: NbMenuItem[] = [
    {
      title: 'Home',
      link: '/home',
      icon: 'home-outline',
      selected: true
    },
    {
      title: 'Assistant Classique',
      link: '/assistants/classic_assistant',
      icon: 'settings-2-outline'
    },
    {
      title: 'Assistant RH',
      link: '/assistants/rh_assistant',
      icon: 'settings-2-outline'
    }
  ];

  constructor(private sidebarService: NbSidebarService, private toastrService: NbToastrService,
              private overlayService: NbOverlayService, http: HttpClient, private authService: AuthService) {
    this.accessToken = this.authService.getToken();
    this.isUserLoggedIn = this.accessToken !== "";
    this.client = http;

    this.getLoggedInUser();
  }

  toggle() {
    this.sidebarService.toggle(true);
    return false;
  }

  logout() {
    this.authService.logout();
  }

  getLoggedInUser() {
    if(this.isUserLoggedIn) {
      this.client.get<User>(`${environment.serverApiUrl}/users/loggedIn`)
        .subscribe((data: User) => {
            this.userId = data.id;
          },
          error => {
            console.error("Error fetching user data: ", error);
          });
    }
  }
}

interface User {
  id: string;
  username: string;
  enabled: boolean;
}
