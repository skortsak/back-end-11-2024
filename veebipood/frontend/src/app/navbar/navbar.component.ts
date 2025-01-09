import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CartService } from '../services/cart.service';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, TranslatePipe],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  loggedIn!: boolean;
  admin!: boolean;
  sum!: number;

  // käivitub kui componendi peale minnakse. Failide sidumiseks (Spring: @Autowired)
  constructor(private authService: AuthService,
    private cartService: CartService,
    private router: Router,
    private translateService: TranslateService
  ) {}

  // käivitub siis kui componendi peale minnakse. Käima minemise funktsioon
  // muutujate initsialiseerimiseks
  ngOnInit(): void {
    //this.authService.determineIfLogedIn().subscribe();
    //this.loggedIn = this.authService.loggedInStatus.loggedIn;
    this.authService.loggedInStatus.subscribe(response => {
      this.loggedIn = response.loggedIn;
      this.admin = response.admin;
    });
    // .subscribe() käivitub iga kord, kui this.authService.loggedInStatus.next() rida käivitatakse
    this.cartService.cartSumSubject.subscribe(response => {
      this.sum = response;
    });
  }

  logout() {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("expiration");
    //this.admin = false; // <- ei muuda Subjecti sees olevaid väärtusi
    //this.loggedIn = false;
    this.authService.loggedInStatus.next({loggedIn: false, admin: false});
    this.router.navigateByUrl("/");
  }

  useLanguage(language: string): void {
    this.translateService.use(language);
  }
}
