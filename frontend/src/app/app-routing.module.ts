import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AccountListComponent } from './components/account-list/account-list.component';
import { TransactionListComponent } from './components/transaction-list/transaction-list.component';
import { NotificationListComponent } from './components/notification-list/notification-list.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: '',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'accounts', component: AccountListComponent },
      { path: 'transactions', component: TransactionListComponent },
      { path: 'notifications', component: NotificationListComponent },
      { path: '', redirectTo: 'accounts', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { } 