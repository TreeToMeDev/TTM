import { AuthHttpInterceptor, AuthModule } from '@auth0/auth0-angular';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { NgChartsModule } from 'ng2-charts';
import { ErrorStateMatcher} from '@angular/material/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MaterialModule } from './shared/modules/material/material.module';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatNativeDateModule } from '@angular/material/core';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTreeModule } from '@angular/material/tree';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar'; 
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { AccountDetailComponent } from './components/accounts/account-detail/account-detail.component';
import { AccountEditComponent } from './components/accounts/account-edit/account-edit.component';
import { AccountListComponent } from './components/accounts/account-list/account-list.component';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { ContactDetailComponent } from './components/contacts/contact-detail/contact-detail.component';
import { ContactEditDialogComponent } from './components/contacts/contact-edit-dialog/contact-edit-dialog.component';
import { ContactEditComponent } from './components/contacts/contact-edit/contact-edit.component';
import { ContactListComponent } from './components/contacts/contact-list/contact-list.component';
import { ContactSubListComponent } from './components/contacts/contact-sub-list/contact-sub-list.component';
import { CustomErrorStateMatcher } from '../app/custom-error-state-matcher';
import { DateUTCPipe } from './components/shared/pipes/date-UTC.pipe';
import { DateTimeLocalPipe } from './components/shared/pipes/date-time-local.pipe';
import { DealDetailComponent } from './components/deals/deal-detail/deal-detail.component';
import { DealDetailDialogComponent } from './components/deals/deal-detail-dialog/deal-detail-dialog.component';
import { DealEditComponent } from './components/deals/deal-edit/deal-edit.component';
import { DealEditDialogComponent } from './components/deals/deal-edit-dialog/deal-edit-dialog.component';
import { DealListComponent } from './components/deals/deal-list/deal-list.component';
import { DealSubListComponent } from './components/deals/deal-sub-list/deal-sub-list.component';
import { DeleteDialogComponent } from './components/shared/dialog/delete-dialog/delete-dialog.component';
import { EnumMapPipe } from './components/shared/pipes/enum-map.pipe';
import { environment } from '../environments/environment';
import { FileSizeFormatPipe } from './components/shared/pipes/file-size-format.pipe';
import { FileUploaderComponent } from './components/file-upload/file-uploader/file-uploader.component';
// TODO: is HttpErrorHandler still useful??
import { HttpErrorHandler } from './http-error-handler';
import { HttpErrorInterceptor } from './core/interceptors/http-error-interceptor';
import { LoadingSpinnerComponent } from './components/shared/loading-spinner/loading-spinner.component';
import { ProductDetailComponent } from './components/products/product-detail/product-detail.component';
import { ProductEditComponent } from './components/products/product-edit/product-edit.component';
import { ProductListComponent } from './components/products/product-list/product-list.component';
import { PurchaseSubListComponent } from './components/purchases/purchase-sub-list/purchase-sub-list.component';
import { PurchaseEditComponent } from './components/purchases/purchase-edit/purchase-edit.component';
import { SignInComponent } from './components/auth/sign-in/sign-in.component';
import { StringDateHttpInterceptor } from './core/interceptors/string-date-interceptor';
import { SykerHeaderComponent } from './components/syker-header/syker-header.component';
import { TaskDetailComponent } from './components/tasks/task-detail/task-detail.component';
import { TaskEditComponent } from './components/tasks/task-edit/task-edit.component';
import { TaskEditDialogComponent } from './components/tasks/task-edit-dialog/task-edit-dialog.component';
import { TaskDetailDialogComponent } from './components/tasks/task-detail-dialog/task-detail-dialog.component';
import { TaskListComponent } from './components/tasks/task-list/task-list.component';
import { TaskSubListComponent } from './components/tasks/task-sub-list/task-sub-list.component';
import { UserDetailComponent } from './components/users/user-detail/user-detail.component';
import { UserEditComponent } from './components/users/user-edit/user-edit.component';
import { UserListComponent } from './components/users/user-list/user-list.component';
import { ReferralSubmissionComponent } from './components/referrals/referral-submission/referral-submission.component';
import { ReferralListComponent } from './components/referrals/referral-list/referral-list.component';
import { ReferralDetailComponent } from './components/referrals/referral-detail/referral-detail.component';
import { NoteEditComponent } from './components/notes/note-edit/note-edit.component';
import { NoteEditDialogComponent } from './components/notes/note-edit-dialog/note-edit-dialog.component';
import { NoteSubListComponent } from './components/notes/note-sub-list/note-sub-list.component';
import { ReferralConvertComponent } from './components/referrals/referral-convert/referral-convert.component';
import { PurchaseEditDialogComponent } from './components/purchases/purchase-edit-dialog/purchase-edit-dialog.component';
import { HistorySubListComponent } from './components/history/history-sub-list/history-sub-list.component';
import { HttpErrorDialogComponent } from './components/http-error-dialog/http-error-dialog.component';
import { AttachmentSubListComponent } from './components/attachments/attachment-sub-list/attachment-sub-list.component';
import { SmallerSpinnerComponent } from './components/shared/smaller-spinner/smaller-spinner.component';
import { DealItemSubListComponent } from './components/deals/deal-item-sub-list/deal-item-sub-list.component';
import { DealItemEditComponent } from './components/deals/deal-item-edit/deal-item-edit.component';
import { InputNumberDirective } from './components/shared/two-digit-decimal/input-number.component';
import { AgentListComponent } from './components/agents/agent-list/agent-list.component';
import { AgentDetailComponent } from './components/agents/agent-detail/agent-detail.component';
import { AgentEditComponent } from './components/agents/agent-edit/agent-edit.component';
import { EmailComponent } from './components/email/email.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DashboardOpenTasksComponent } from './components/dashboard/dashboard-open-tasks/dashboard-open-tasks.component';
import { DashboardNewLeadsComponent } from './components/dashboard/dashboard-new-leads/dashboard-new-leads.component';
import { DashboardAccountTypeTotalsComponent } from './components/dashboard/dashboard-account-type-totals/dashboard-account-type-totals.component';
import { DashboardAccountCountryTotalsComponent } from './components/dashboard/dashboard-account-country-totals/dashboard-account-country-totals.component';
import { EmailSubListComponent } from './components/email/email-sub-list/email-sub-list.component';
import { EmailEditDialogComponent } from './components/email/email-edit-dialog/email-edit-dialog.component';
import { EmailEditComponent } from './components/email/email-edit/email-edit.component';

@NgModule({
  declarations: [
    AccountEditComponent,
    AccountListComponent,
    AccountDetailComponent,
    AppComponent,
    ContactDetailComponent,
    ContactEditComponent,
    ContactEditDialogComponent,
    ContactListComponent,
    ContactSubListComponent,
    DateTimeLocalPipe,
    DateUTCPipe,
    DeleteDialogComponent,
    EnumMapPipe,
    FileSizeFormatPipe,
    FileUploaderComponent,
    LoadingSpinnerComponent,
    NoteSubListComponent,
    ProductDetailComponent,
    ProductEditComponent,
    ProductListComponent,
    PurchaseSubListComponent,
    PurchaseEditComponent,
    SignInComponent,
    SykerHeaderComponent,
    TaskDetailComponent,
    TaskEditComponent,
    TaskEditDialogComponent,
    TaskDetailDialogComponent,
    TaskListComponent,
    TaskSubListComponent,
    UserDetailComponent,
    UserEditComponent,
    UserListComponent,
    ReferralSubmissionComponent,
    ReferralListComponent,
    ReferralDetailComponent,
    NoteEditComponent,
    NoteEditDialogComponent,
    NoteSubListComponent,
    ReferralConvertComponent,
    PurchaseEditDialogComponent,
    HistorySubListComponent,
    HttpErrorDialogComponent,
    AttachmentSubListComponent,
    DealListComponent,
    DealDetailComponent,
    DealEditComponent,
    DealDetailDialogComponent,
    DealEditDialogComponent,
    DealSubListComponent,
    SmallerSpinnerComponent,
    DealItemSubListComponent,
    DealItemEditComponent,
    InputNumberDirective,
    AgentListComponent,
    AgentDetailComponent,
    AgentEditComponent,
    EmailComponent,
    DashboardComponent,
    DashboardOpenTasksComponent,
    DashboardNewLeadsComponent,
    DashboardAccountTypeTotalsComponent,
    DashboardAccountCountryTotalsComponent,
    InputNumberDirective,
    EmailSubListComponent,
    EmailEditDialogComponent,
    EmailEditComponent
  ],
  imports: [
    AppRoutingModule,
    AuthModule.forRoot({
      domain: environment.auth0Domain,
      clientId: environment.auth0ClientId,
      audience: environment.auth0Audience,
      httpInterceptor: {
        allowedList: [`${environment.apiUrl}rest/*`],
      }
    }),
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatDialogModule,
    MaterialModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,
    MatNativeDateModule,
    MatTreeModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatSelectModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    NgChartsModule,
    ReactiveFormsModule
	],
  providers: [
    HttpErrorHandler,
    MatDatepickerModule,
    MatNativeDateModule,
    { 
      provide: ErrorStateMatcher, 
      useClass: CustomErrorStateMatcher
    }, {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthHttpInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true,
    },  
    {
      provide: HTTP_INTERCEPTORS,
      useClass: StringDateHttpInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
