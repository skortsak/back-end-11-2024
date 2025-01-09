import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  message = "";

  constructor(private authService: AuthService,
    private router: Router
  ) {}

  onLogin(form: NgForm) {
    this.authService.login(form.value).subscribe({
      next: (res) => {
        sessionStorage.setItem("token", res.token);
        sessionStorage.setItem("expiration", res.expiration.toString());
        if(res.admin) {
          this.router.navigateByUrl("/admin");
        } else {
          this.router.navigateByUrl("/");
        }
      },
      error: (res) => {
        this.message = res.error.name
                  // = res.error.date
                  // = res.error.statusCode <-- ErrorMessage mudelist
      }
    });
  }
}
