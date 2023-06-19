export interface EmailRequest {
    body: string;
    recipient: string;
    sourceUrl: string; // from where they initiated the email in our app, required for Google authorization flow
    subject: string;
}
  