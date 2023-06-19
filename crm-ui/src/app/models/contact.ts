import { ContactType } from "./contact-type";
import { ContactTypeListItem } from "./contact-type-list-item";

export interface Contact {
  accountId: number,
  accountCity: string,
  accountCountry: string,
  accountIndustry: string,
  accountName: string,
	accountPhone: string,
	accountWebSite: string,
  address: string;
  addTimestamp: Date;
  agentCode: string; // TODO WTF is this for?
  agentId: number;
  agentName: string;
  city: string;
  country: string;
  code: string; // TODO WTF IS THIS FOR ????
  contactTypes: ContactType[];
  department: string;
  email: string;
  fileId: number;
  firstName: string;
  id: number;
  language: string;
  lastChangeAction: string;
  lastChangeDescription: string;
  lastChangeTimestamp: Date;
  lastChangeUserName: string;
  lastName: string;
  notes: string; // TODO SHOULD BE IN NOTES TABLE ????
  originalFileName: string;
  postalCode: string;
  phone: string;
  phoneCell: string;
  provinceState: string;
  referralId: number;
  referrerName: string;
  source: string;
  street: string;
  title: string;
}
