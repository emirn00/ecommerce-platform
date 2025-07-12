import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth-service';

@Component({
  selector: 'app-register-page',
  standalone: false,
  templateUrl: './register-page.html',
  styleUrl: './register-page.scss'
})
export class RegisterPage implements OnInit{

  registerForm!: FormGroup;
  errorMessage = '';
  successMessage = '';

  constructor(private fb : FormBuilder ,private authService : AuthService) {
  }

  ngOnInit() {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe({
        next: () => {
          this.successMessage = 'Kayıt başarılı! Giriş yapabilirsiniz.';
          this.errorMessage = '';
          this.registerForm.reset();
        },
        error: (err) => {
          this.errorMessage = 'Kayıt başarısız: ' + (err.error?.message || err.statusText);
          this.successMessage = '';
        }
      });
    }
  }

}
