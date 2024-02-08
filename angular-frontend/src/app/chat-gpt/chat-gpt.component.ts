import {Component, OnDestroy, OnInit} from "@angular/core";
import {NbMenuItem, NbOverlayService, NbSidebarService, NbToastrService} from "@nebular/theme";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environment/environment";
import {AuthService} from "../auth/auth.service";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: "app-chat-gpt",
  templateUrl: "./chat-gpt.component.html",
  styleUrls: ["./chat-gpt.component.scss"]
})

export class ChatGPTComponent implements OnInit, OnDestroy {
  messages: any[];
  accessToken: string;
  client: HttpClient;
  userId: any;
  userName: any;
  isUserLoggedIn: boolean;
  assistantType: string = "classic_assistant";
  private routeSub: Subscription;
  sidebarItems: NbMenuItem[] = [
    {
      title: 'Home',
      link: '/home',
      icon: 'home-outline'
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

  constructor(private sidebarService: NbSidebarService, private toastrService: NbToastrService, private route: ActivatedRoute,
              private overlayService: NbOverlayService, http: HttpClient, private authService: AuthService) {
    let files;
    let items = [{
      // text: "Hello, how can I help you?",
      text: "Bonjour, comment puis-je vous aider?",
      date: new Date(),
      reply: true,
      type: "text",
      files: files,
      user: {
        name: "DLAB Assistant",
        avatar: "https://i.gifer.com/no.gif"
      }
    }];
    this.messages = items;
    this.accessToken = this.authService.getToken();
    this.isUserLoggedIn = this.accessToken !== "";
    this.client = http;
    this.getLoggedInUser();
  }

  ngOnInit() {
    this.routeSub = this.route.paramMap.subscribe(params => {
      const assistantTypeRequestParam = this.route.snapshot.paramMap.get('type') || "";

      this.initializeAssistantType(assistantTypeRequestParam);
    });
  }

  initializeAssistantType(assistantTypeRequestParam: string) {
    this.assistantType = assistantTypeRequestParam;

    this.getConversation();
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
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
            this.userName = data.username;
          },
          error => {
            console.error("Error fetching user data: ", error);
          });
    }
  }

  sendRequest(promptItem: string): Promise<any> {
    if (promptItem === null || promptItem === undefined || promptItem === "") {
      return Promise.resolve("");
    }

    const myHeaders: HttpHeaders = new HttpHeaders();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Authorization", `Bearer ${this.accessToken}`);

    let params = new HttpParams();
    params = params.append("prompt", promptItem);

    return new Promise((resolve, reject) => {
      this.client.post(`${environment.serverApiUrl}/assistants/`.concat(this.assistantType).concat('/completion'), {
        headers: myHeaders
      }, { params }).subscribe((data) => {
        const messageItem: any = data;
        let theUser = {
          name: "Assistant",
          avatar: "https://cdn2.iconfinder.com/data/icons/boxicons-solid-vol-1/24/bxs-bot-512.png"
        };

        let newVar = {
          text: messageItem.content.trim(),
          date: Date.now(),
          reply: false,
          type: "text",
          user: theUser
        };
        resolve(newVar);
      }, (error) => {
        reject(error);
      });
    });
  };

  getConversation() {
    const myHeaders: HttpHeaders = new HttpHeaders();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", `Bearer ${this.accessToken}`);

    this.client.get(`${environment.serverApiUrl}/assistants/`.concat(this.assistantType).concat('/allmessages'), {
      headers: myHeaders
    }).subscribe((data) => {
      // @ts-ignore
      if (data["error"]) {
        // @ts-ignore
        this.toastrService.danger(data["message"], "Error", { duration: 5000 });
        return;
      }

      const dataArray = data as any[]; // cast data to an array

      if (Object.keys(data).length === 0) {
        return;
      }

      let allMessages: any[] = [];
      dataArray.forEach((messageItem: any) => {
        let isReply = messageItem.author === this.userId;
        let theUser;
        if (isReply) {
          theUser = {
            avatar: "https://cdn2.iconfinder.com/data/icons/ios-7-icons/50/user_male2-512.png"
          };
        } else {
          theUser = {
            name: "Assistant",
            avatar: "https://cdn2.iconfinder.com/data/icons/boxicons-solid-vol-1/24/bxs-bot-512.png"
          };
        }

        let message = {
          text: messageItem.text,
          date: messageItem.createdAt,
          reply: isReply,
          type: "text",
          user: theUser
        };
        allMessages.push(message);
      });

      this.messages = allMessages;

    }, (error) => {
      this.toastrService.danger(error.error.message || "Erreur inconnu, veuillez contacter l'administrateur !", "Error", { duration: 5000 });
    });

  };

  sendMessage(event: any) {

    this.messages.push({
      text: event.message,
      date: new Date(),
      reply: true,
      type: "text",
      user: {
        name: this.userName,
        avatar: "https://cdn2.iconfinder.com/data/icons/ios-7-icons/50/user_male2-512.png"
      }
    });

    this.sendRequest(event.message).then((message) => {
      let botReply = true;
      if (message === null || message === undefined || message === "") {
        botReply = false;
      }
      if (botReply) {
        setTimeout(() => {
          this.messages.push(message);
        }, 1500);
      }
    });
  }
}

interface User {
  id: string;
  username: string;
  enabled: boolean;
}
