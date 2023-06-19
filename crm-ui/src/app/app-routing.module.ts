import { AuthGuard } from '@auth0/auth0-angular';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AccountDetailComponent } from './components/accounts/account-detail/account-detail.component';
import { AccountEditComponent } from './components/accounts/account-edit/account-edit.component';
import { AccountListComponent } from './components/accounts/account-list/account-list.component';
import { AgentDetailComponent } from './components/agents/agent-detail/agent-detail.component';
import { AgentEditComponent } from './components/agents/agent-edit/agent-edit.component';
import { AgentListComponent } from './components/agents/agent-list/agent-list.component';
import { ContactDetailComponent } from './components/contacts/contact-detail/contact-detail.component';
import { ContactEditComponent } from './components/contacts/contact-edit/contact-edit.component';
import { ContactListComponent } from './components/contacts/contact-list/contact-list.component';
import { DealDetailComponent } from './components/deals/deal-detail/deal-detail.component';
import { DealEditComponent } from './components/deals/deal-edit/deal-edit.component';
import { DealListComponent } from './components/deals/deal-list/deal-list.component';
import { EmailComponent } from './components/email/email.component';
import { FileUploaderComponent } from './components/file-upload/file-uploader/file-uploader.component';
import { ProductDetailComponent } from './components/products/product-detail/product-detail.component';
import { ProductEditComponent } from './components/products/product-edit/product-edit.component';
import { ProductListComponent } from './components/products/product-list/product-list.component';
import { PurchaseEditComponent } from './components/purchases/purchase-edit/purchase-edit.component';
import { ReferralConvertComponent } from './components/referrals/referral-convert/referral-convert.component';
import { ReferralDetailComponent } from './components/referrals/referral-detail/referral-detail.component';
import { ReferralListComponent } from './components/referrals/referral-list/referral-list.component';
import { ReferralSubmissionComponent } from './components/referrals/referral-submission/referral-submission.component';
import { SignInComponent } from './components/auth/sign-in/sign-in.component';
import { TaskDetailComponent } from './components/tasks/task-detail/task-detail.component';
import { TaskEditComponent } from './components/tasks/task-edit/task-edit.component';
import { TaskListComponent } from './components/tasks/task-list/task-list.component';
import { UserDetailComponent } from './components/users/user-detail/user-detail.component';
import { UserEditComponent } from './components/users/user-edit/user-edit.component';
import { UserListComponent } from './components/users/user-list/user-list.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

const routes: Routes = [
  
  { path: 'accounts', component: AccountListComponent, canActivate: [AuthGuard] },
  { path: 'accounts/new', component: AccountEditComponent, canActivate: [AuthGuard] },
  { path: 'accounts/:id', component: AccountDetailComponent, canActivate: [AuthGuard] },
  { path: 'accounts/:id/duplicate', component: AccountEditComponent, canActivate: [AuthGuard] },
  { path: 'accounts/:id/edit', component: AccountEditComponent, canActivate: [AuthGuard] },

  { path: 'agents', component: AgentListComponent, canActivate: [AuthGuard] },
  { path: 'agents/new', component: AgentEditComponent, canActivate: [AuthGuard] },
  { path: 'agents/:id', component: AgentDetailComponent, canActivate: [AuthGuard] },
  { path: 'agents/:id/edit', component: AgentEditComponent, canActivate: [AuthGuard] },
  
  { path: 'contacts', component: ContactListComponent, canActivate: [AuthGuard] },
  // important, Edit must appear before Detail otherwise it routes to Detail with an ID of 'new'
  { path: 'contacts/new', component: ContactEditComponent, canActivate: [AuthGuard] },
  { path: 'contacts/:id', component: ContactDetailComponent, canActivate: [AuthGuard] },
  { path: 'contacts/:id/duplicate', component: ContactEditComponent, canActivate: [AuthGuard] },
  { path: 'contacts/:id/edit', component: ContactEditComponent, canActivate: [AuthGuard] },

  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  
  { path: 'deals', component: DealListComponent, canActivate: [AuthGuard] },
  { path: 'deals/new', component: DealEditComponent, canActivate: [AuthGuard] },
  { path: 'deals/:id', component: DealDetailComponent, canActivate: [AuthGuard] },
  { path: 'deals/:id/edit', component: DealEditComponent, canActivate: [AuthGuard] },

  { path: 'email', component: EmailComponent, canActivate: [AuthGuard] },

  { path: 'products', component: ProductListComponent, canActivate: [AuthGuard] },
  { path: 'products/new', component: ProductEditComponent, canActivate: [AuthGuard] },
  { path: 'products/:id', component: ProductDetailComponent, canActivate: [AuthGuard] },
  { path: 'products/:id/edit', component: ProductEditComponent, canActivate: [AuthGuard] },
  
  { path: 'purchases/new/:accountId', component: PurchaseEditComponent, canActivate: [AuthGuard] },
  { path: 'purchases/:id/edit', component: PurchaseEditComponent, canActivate: [AuthGuard] },

  { path: 'referrals', component: ReferralListComponent, canActivate: [AuthGuard] },
  { path: 'referrals/:id', component: ReferralDetailComponent, canActivate: [AuthGuard] },
  { path: 'referrals/:id/convert', component: ReferralConvertComponent, canActivate: [AuthGuard] },
   
  { path: 'tasks', component: TaskListComponent, canActivate: [AuthGuard] },
  { path: 'tasks/new', component: TaskEditComponent, canActivate: [AuthGuard] },
  { path: 'tasks/:id', component: TaskDetailComponent, canActivate: [AuthGuard] },
  { path: 'tasks/:id/edit', component: TaskEditComponent, canActivate: [AuthGuard] },

  { path: 'users', component: UserListComponent, canActivate: [AuthGuard] },
  { path: 'users/new', component: UserEditComponent, canActivate: [AuthGuard] },
  { path: 'users/:id', component: UserDetailComponent, canActivate: [AuthGuard] },
  { path: 'users/:id/edit', component: UserEditComponent, canActivate: [AuthGuard] },

  { path: 'file-uploader', component: FileUploaderComponent, canActivate: [AuthGuard] },

  { path: 'referral-form', component: ReferralSubmissionComponent},

  { path: 'sign-in', component: SignInComponent },
  { path: '', redirectTo: '/sign-in', pathMatch: 'full' },
  { path: '**', redirectTo: '/sign-in', pathMatch: 'full' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }