package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.menus.inventory.*;
import com.wodder.inventory.console.models.*;
import com.wodder.inventory.dtos.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateItemHandlerTest extends BaseMenuTest {

	@Captor
	ArgumentCaptor<InventoryItemDto> captor;

	@Mock
	InventoryItemModel model;

	@Test
	@DisplayName("Can update an item")
	void updateItem() {
		when(model.updateItem(any())).thenReturn(new Result<>(InventoryItemDto.builder().build(), null));
		setChars("id=1 name=milk");
		UpdateItemHandler handler = new UpdateItemHandler(model);
		handler.handleInput(in, out, err);
		verify(model).updateItem(captor.capture());

		InventoryItemDto dto = captor.getValue();
		assertNotNull(dto);
		assertEquals(1L, dto.getId());
		assertEquals("milk", dto.getName());
		assertEquals(String.format("Item updated successfully%n"), baosOut.toString());
	}

	@Test
	@DisplayName("Displays a failure message when item cannot be updated")
	void updateItemFailure() {
		when(model.updateItem(any())).thenReturn(new Result<>(null, "Exception during creation"));
		setChars("id=1 name=milk");
		UpdateItemHandler handler = new UpdateItemHandler(model);
		handler.handleInput(in, out, err);
		String expected = String.format("Unable to update item%n");
		assertEquals(expected, baosErr.toString());
	}


}
