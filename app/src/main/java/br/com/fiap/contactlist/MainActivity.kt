package br.com.fiap.contactlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.contactlist.database.repository.ContactRepository
import br.com.fiap.contactlist.model.Contact
import br.com.fiap.contactlist.ui.theme.ContactListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactScreen()
                }
            }
        }
    }
}

@Composable
fun ContactScreen() {
    var nomeState by remember {
        mutableStateOf("")
    }

    var telefoneState by remember {
        mutableStateOf("")
    }

    var friendState by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val contactRepository = ContactRepository(context)

    var listContactState by remember {
        mutableStateOf(contactRepository.listContacts())
    }

    Column {
        Contact(
            name = nomeState,
            telefone = telefoneState,
            friend = friendState,
            onNameChange = {
                nomeState = it
            },
            onPhoneChange = {
                telefoneState = it
            },
            onFriendChange = {
                friendState = it
            },
            update = {
                listContactState = contactRepository.listContacts()
            }
        )
        ContactList(
            listContactState,
            update = { listContactState = contactRepository.listContacts() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Contact(
    name: String,
    telefone: String,
    friend: Boolean,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onFriendChange: (Boolean) -> Unit,
    update: () -> Unit
) {
    val context = LocalContext.current
    val contactRepository = ContactRepository(context)
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Cadastro de contatos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE91E63)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { onNameChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Nome do Contato")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = telefone,
            onValueChange = { onPhoneChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Telefone do contato")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = friend,
                onCheckedChange = {
                    onFriendChange(it)
                }
            )
            Text(text = "Amigo")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val contact = Contact(
                    id = 0,
                    name = name,
                    phone = telefone,
                    isFriend = friend
                )
                contactRepository.insert(contact)
                update()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "CADASTRAR",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ContactList(listContact: List<Contact>, update: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        for (contact in listContact) {
            ContactCard(contact, update)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun ContactCard(contact: Contact, update: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        val context = LocalContext.current
        val contactRepository = ContactRepository(context)

        var listContactState by remember {
            mutableStateOf(contactRepository.listContacts())
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(2f)
            ) {
                Text(
                    text = contact.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = contact.phone,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = if(contact.isFriend) "Amigo" else "Contato",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            IconButton(onClick = {
                val contactRepository = ContactRepository(context = context)
                contactRepository.delete(contact = contact)
                update()
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }
    }
}

